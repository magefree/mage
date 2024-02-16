package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenAllEffect;
import mage.abilities.effects.common.cost.SpellsCostModificationThatTargetSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.game.permanent.token.PursuedWhaleToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PursuedWhale extends CardImpl {

    private static final FilterCard filter = new FilterCard("spells");

    public PursuedWhale(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{U}");

        this.subtype.add(SubType.WHALE);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // When Pursued Whale enters the battlefield, each opponent creates a 1/1 red Pirate creature token with "This creature can't block" and "Creatures you control attack each combat if able."
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenAllEffect(
                new PursuedWhaleToken(), TargetController.OPPONENT
        )));

        // Spells your opponents cast that target Pursued Whale cost {3} more to cast.
        this.addAbility(new SimpleStaticAbility(
                new SpellsCostModificationThatTargetSourceEffect(3, filter, TargetController.OPPONENT)
        ));
    }

    private PursuedWhale(final PursuedWhale card) {
        super(card);
    }

    @Override
    public PursuedWhale copy() {
        return new PursuedWhale(this);
    }
}
