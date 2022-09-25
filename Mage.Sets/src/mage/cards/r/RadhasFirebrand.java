package mage.cards.r;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.costadjusters.DomainAdjuster;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.combat.CantBlockTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.filter.predicate.permanent.DefendingPlayerControlsPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RadhasFirebrand extends CardImpl {

    private static final FilterPermanent filter
            = new FilterCreaturePermanent("creature defending player controls with power less than {this}'s power");

    static {
        filter.add(DefendingPlayerControlsPredicate.instance);
        filter.add(RadhasFirebrandPredicate.instance);
    }

    public RadhasFirebrand(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever Radha's Firebrand attacks, target creature defending player controls with power less than Radha's Firebrand's power can't block this turn.
        Ability ability = new AttacksTriggeredAbility(new CantBlockTargetEffect(Duration.EndOfTurn));
        ability.addTarget(new TargetPermanent(filter));
        this.addAbility(ability);

        // Domain -- {5}{R}: Radha's Firebrand gets +2/+2 until end of turn. This ability costs {1} less to activate for each basic land type among lands you control. Activate only once each turn.
        ability = new LimitedTimesPerTurnActivatedAbility(
                Zone.BATTLEFIELD,
                new BoostSourceEffect(2, 2, Duration.EndOfTurn),
                new ManaCostsImpl<>("{5}{R}")
        );
        ability.addEffect(new InfoEffect("This ability costs {1} less to activate " +
                "for each basic land type among lands you control."));
        ability.addHint(DomainHint.instance);
        ability.setAbilityWord(AbilityWord.DOMAIN);
        ability.setCostAdjuster(DomainAdjuster.instance);
        this.addAbility(ability);
    }

    private RadhasFirebrand(final RadhasFirebrand card) {
        super(card);
    }

    @Override
    public RadhasFirebrand copy() {
        return new RadhasFirebrand(this);
    }
}

enum RadhasFirebrandPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        Permanent permanent = input.getSource().getSourcePermanentOrLKI(game);
        return permanent != null && permanent.getPower().getValue() > input.getObject().getPower().getValue();
    }
}
