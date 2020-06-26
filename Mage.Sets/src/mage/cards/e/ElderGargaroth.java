package mage.cards.e;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksOrBlocksTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.keyword.ReachAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.BeastToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ElderGargaroth extends CardImpl {

    public ElderGargaroth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever Elder Gargaroth attacks or blocks, choose one —
        // • Create a 3/3 green Beast creature token.
        Ability ability = new AttacksOrBlocksTriggeredAbility(new CreateTokenEffect(new BeastToken()), false);

        // • You gain 3 life.
        ability.addMode(new Mode(new GainLifeEffect(3)));

        // • Draw a card.
        ability.addMode(new Mode(new DrawCardSourceControllerEffect(1)));
        this.addAbility(ability);
    }

    private ElderGargaroth(final ElderGargaroth card) {
        super(card);
    }

    @Override
    public ElderGargaroth copy() {
        return new ElderGargaroth(this);
    }
}
