package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.MageSingleton;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.*;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.Game;

import java.util.*;

/**
 * @author TheElk801
 */
public final class ThunderousOrator extends CardImpl {

    public ThunderousOrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever Thunderous Orator attacks, it gains flying until end of turn if you control a creature with flying. The same is true for first strike, double strike, deathtouch, indestructible, lifelink, menace, and trample.
        this.addAbility(new AttacksTriggeredAbility(new ThunderousOratorEffect(), false));
    }

    private ThunderousOrator(final ThunderousOrator card) {
        super(card);
    }

    @Override
    public ThunderousOrator copy() {
        return new ThunderousOrator(this);
    }
}

class ThunderousOratorEffect extends OneShotEffect {

    private static final FilterPermanent filter = new FilterControlledCreaturePermanent();

    static {
        filter.add(new AbilityPredicate(MenaceAbility.class));
    }

    private static final List<MageSingleton> keywords = Arrays.asList(
            FlyingAbility.getInstance(),
            FirstStrikeAbility.getInstance(),
            DoubleStrikeAbility.getInstance(),
            DeathtouchAbility.getInstance(),
            IndestructibleAbility.getInstance(),
            LifelinkAbility.getInstance(),
            TrampleAbility.getInstance()
    );

    ThunderousOratorEffect() {
        super(Outcome.Benefit);
        staticText = "it gains flying until end of turn if you control a creature with flying. The same is true " +
                "for first strike, double strike, deathtouch, indestructible, lifelink, menace, and trample";
    }

    private ThunderousOratorEffect(final ThunderousOratorEffect effect) {
        super(effect);
    }

    @Override
    public ThunderousOratorEffect copy() {
        return new ThunderousOratorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Ability> abilities = new ArrayList<>();
        if (game.getBattlefield().count(filter, source.getControllerId(), source, game) > 0) {
            abilities.add(new MenaceAbility());
        }
        game.getBattlefield()
                .getActivePermanents(
                        StaticFilters.FILTER_CONTROLLED_CREATURE,
                        source.getControllerId(), source, game
                ).stream()
                .filter(Objects::nonNull)
                .map(p -> p.getAbilities(game))
                .flatMap(Collection::stream)
                .filter(keywords::contains)
                .distinct()
                .forEach(abilities::add);
        if (abilities.isEmpty()) {
            return false;
        }
        for (Ability ability : abilities) {
            game.addEffect(new GainAbilitySourceEffect(ability, Duration.EndOfTurn), source);
        }
        return true;
    }
}
