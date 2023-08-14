package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.WalkerToken;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DarylHunterOfWalkers extends CardImpl {

    private static final FilterPermanent filter
            = new FilterPermanent(SubType.ZOMBIE, "a Zombie an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public DarylHunterOfWalkers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARCHER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // At the beginning of your upkeep, target opponent creates three Walker tokens.
        Ability ability = new BeginningOfUpkeepTriggeredAbility(
                new CreateTokenTargetEffect(new WalkerToken(), 3),
                TargetController.YOU, false
        );
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);

        // {T}: Daryl deals 2 damage to target creature.
        ability = new SimpleActivatedAbility(new DamageTargetEffect(2), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Whenever a Zombie an opponent controls dies, draw a card.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new DrawCardSourceControllerEffect(1), false, filter
        ));
    }

    private DarylHunterOfWalkers(final DarylHunterOfWalkers card) {
        super(card);
    }

    @Override
    public DarylHunterOfWalkers copy() {
        return new DarylHunterOfWalkers(this);
    }
}
