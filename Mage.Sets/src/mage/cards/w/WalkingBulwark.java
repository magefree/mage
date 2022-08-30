package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CanAttackAsThoughItDidntHaveDefenderTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.filter.predicate.mageobject.MageObjectReferencePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WalkingBulwark extends CardImpl {

    private static final FilterPermanent filter = new FilterCreaturePermanent("creature with defender");

    static {
        filter.add(new AbilityPredicate(DefenderAbility.class));
    }

    public WalkingBulwark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{1}");

        this.subtype.add(SubType.GOLEM);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // {2}: Until end of turn, target creature with defender gains haste, can attack as though it didn't have defender, and assigns combat damage equal to its toughness rather than its power. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new WalkingBulwarkEffect(), new GenericManaCost(2));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);
    }

    private WalkingBulwark(final WalkingBulwark card) {
        super(card);
    }

    @Override
    public WalkingBulwark copy() {
        return new WalkingBulwark(this);
    }
}

class WalkingBulwarkEffect extends OneShotEffect {

    WalkingBulwarkEffect() {
        super(Outcome.Benefit);
        staticText = "until end of turn, " +
                "target creature with defender gains haste, " +
                "can attack as though it didn't have defender, " +
                "and assigns combat damage equal to its toughness rather than its power";
    }

    private WalkingBulwarkEffect(final WalkingBulwarkEffect effect) {
        super(effect);
    }

    @Override
    public WalkingBulwarkEffect copy() {
        return new WalkingBulwarkEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(getTargetPointer().getFirst(game, source));
        if (permanent == null) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn), source);
        game.addEffect(new CanAttackAsThoughItDidntHaveDefenderTargetEffect(Duration.EndOfTurn), source);
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new MageObjectReferencePredicate(permanent, game));
        game.addEffect(new CombatDamageByToughnessEffect(filter, false), source);
        return true;
    }
}
