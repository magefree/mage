package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.keyword.MyriadAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;
import mage.target.targetpointer.EachTargetPointer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HammersOfMoradin extends CardImpl {

    public HammersOfMoradin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Myriad
        this.addAbility(new MyriadAbility());

        // Whenever Hammers of Moradin attacks, for each opponent, tap up to one target creature that player controls.
        this.addAbility(new AttacksTriggeredAbility(
                new TapTargetEffect()
                        .setTargetPointer(new EachTargetPointer())
                        .setText("for each opponent, tap up to one target creature that player controls")
        ).setTargetAdjuster(HammersOfMoradinAdjuster.instance));
    }

    private HammersOfMoradin(final HammersOfMoradin card) {
        super(card);
    }

    @Override
    public HammersOfMoradin copy() {
        return new HammersOfMoradin(this);
    }
}

enum HammersOfMoradinAdjuster implements TargetAdjuster {
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
            TargetPermanent target = new TargetPermanent(0, 1, filter);
            ability.addTarget(target);
        }
    }
}