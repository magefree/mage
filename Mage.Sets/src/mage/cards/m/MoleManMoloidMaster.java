package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ruleModifying.PlayFromGraveyardControllerEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.MoloidToken;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class MoleManMoloidMaster extends CardImpl {

    public MoleManMoloidMaster(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // You may play lands from your graveyard.
        this.addAbility(new SimpleStaticAbility(PlayFromGraveyardControllerEffect.playLands()));

        // Landfall -- Whenever a land you control enters, create a 1/1 green Minion creature token named Moloid with "Whenever this token attacks, you may mill a card."
        this.addAbility(new LandfallAbility(new CreateTokenEffect(new MoloidToken())));
    }

    private MoleManMoloidMaster(final MoleManMoloidMaster card) {
        super(card);
    }

    @Override
    public MoleManMoloidMaster copy() {
        return new MoleManMoloidMaster(this);
    }
}
