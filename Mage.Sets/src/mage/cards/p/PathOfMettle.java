package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.mana.AnyColorManaAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.RandomUtil;
import mage.watchers.common.AttackedThisTurnWatcher;

import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author LevelX2
 */
public final class PathOfMettle extends TransformingDoubleFacedCard {

    private static final FilterCreaturePermanent filterDamage = new FilterCreaturePermanent("creature that doesn't have first strike, double strike, vigilance, or haste");
    private static final FilterCreaturePermanent filterTransform = new FilterCreaturePermanent("creatures that have first strike, double strike, vigilance, and/or haste");
    private static final String triggerPhrase = "Whenever you attack with at least two " + filterTransform.getMessage() + ", ";

    static {
        filterDamage.add(Predicates.not(Predicates.or(
                new AbilityPredicate(FirstStrikeAbility.class),
                new AbilityPredicate(DoubleStrikeAbility.class),
                new AbilityPredicate(VigilanceAbility.class),
                new AbilityPredicate(HasteAbility.class)
        )));

        filterTransform.add(Predicates.or(
                new AbilityPredicate(FirstStrikeAbility.class),
                new AbilityPredicate(DoubleStrikeAbility.class),
                new AbilityPredicate(VigilanceAbility.class),
                new AbilityPredicate(HasteAbility.class)
        ));
    }

    public PathOfMettle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT}, new SubType[]{}, "{R}{W}",
                "Metzali, Tower of Triumph",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.LAND}, new SubType[]{}, ""
        );

        // Path of Mettle
        // When Path of Mettle enters the battlefield, it deals 1 damage to each creature that doesn't have first strike, double strike, vigilance, or haste.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(new DamageAllEffect(1, "it", filterDamage)));

        // Whenever you attack with at least two creatures that have first strike, double strike, vigilance, and/or haste, transform Path of Mettle.
        this.getLeftHalfCard().addAbility(new AttacksWithCreaturesTriggeredAbility(new TransformSourceEffect(), 2, filterTransform).setTriggerPhrase(triggerPhrase));

        // Metzali, Tower of Triumph
        // {t}: Add one mana of any color.
        this.getRightHalfCard().addAbility(new AnyColorManaAbility());

        // {1}{R}, {T}: Metzali, Tower of Triumph deals 2 damage to each opponent.
        Ability ability = new SimpleActivatedAbility(new DamagePlayersEffect(2, TargetController.OPPONENT), new ManaCostsImpl<>("{1}{R}"));
        ability.addCost(new TapSourceCost());
        this.getRightHalfCard().addAbility(ability);

        // {2}{W}, {T}: Choose a creature at random that attacked this turn. Destroy that creature.
        ability = new SimpleActivatedAbility(new MetzaliTowerOfTriumphDestroyEffect(), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new TapSourceCost());
        this.getRightHalfCard().addAbility(ability);
    }

    private PathOfMettle(final PathOfMettle card) {
        super(card);
    }

    @Override
    public PathOfMettle copy() {
        return new PathOfMettle(this);
    }
}

class MetzaliTowerOfTriumphDestroyEffect extends OneShotEffect {

    MetzaliTowerOfTriumphDestroyEffect() {
        super(Outcome.DestroyPermanent);
        this.staticText = "choose a creature at random that attacked this turn. Destroy that creature";
    }

    private MetzaliTowerOfTriumphDestroyEffect(final MetzaliTowerOfTriumphDestroyEffect effect) {
        super(effect);
    }

    @Override
    public MetzaliTowerOfTriumphDestroyEffect copy() {
        return new MetzaliTowerOfTriumphDestroyEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = RandomUtil.randomFromCollection(
                game.getState()
                        .getWatcher(AttackedThisTurnWatcher.class)
                        .getAttackedThisTurnCreatures()
                        .stream()
                        .map(mor -> mor.getPermanent(game))
                        .filter(Objects::nonNull)
                        .collect(Collectors.toSet())
        );
        return permanent != null && permanent.destroy(source, game);
    }
}
