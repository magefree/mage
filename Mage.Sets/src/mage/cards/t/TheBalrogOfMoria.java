package mage.cards.t;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CycleTriggeredAbility;
import mage.abilities.common.DiesSourceTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DoWhenCostPaid;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.CyclingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.game.permanent.token.TreasureToken;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class TheBalrogOfMoria extends CardImpl {

    public TheBalrogOfMoria(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // When The Balrog of Moria dies, you may exile it. When you do, for each opponent, exile up to one target creature that player controls.
        ReflexiveTriggeredAbility reflexiveAbility = new ReflexiveTriggeredAbility(
            new ExileTargetEffect()
                .setText("for each opponent, exile up to one target creature that player controls."),
            false
        );
        reflexiveAbility.setTargetAdjuster(TheBalrogOfMoriaAdjuster.instance);

        this.addAbility(new DiesSourceTriggeredAbility(
            new DoWhenCostPaid(
                reflexiveAbility,
                new ExileSourceFromGraveCost().setText("exile it"),
                "Exile {this}?"
            ).setTargetPointer(new EachTargetPointer())
        ));

        // Cycling {3}{R}
        this.addAbility(new CyclingAbility(new ManaCostsImpl<>("{3}{R}")));

        // When you cycle The Balrog of Moria, create two Treasure tokens.
        this.addAbility(new CycleTriggeredAbility(new CreateTokenEffect(new TreasureToken(), 2), false));
    }

    private TheBalrogOfMoria(final TheBalrogOfMoria card) {
        super(card);
    }

    @Override
    public TheBalrogOfMoria copy() {
        return new TheBalrogOfMoria(this);
    }
}

enum TheBalrogOfMoriaAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        ability.getTargets().clear();
        for (UUID opponentId : game.getOpponents(ability.getControllerId())) {
            Player opponent = game.getPlayer(opponentId);
            if (opponent == null) {
                continue;
            }
            FilterPermanent filter = new FilterCreaturePermanent("creature controlled by " + opponent.getLogName());
            filter.add(new ControllerIdPredicate(opponentId));
            ability.addTarget(new TargetPermanent(0, 1, filter, false));
        }
    }
}
