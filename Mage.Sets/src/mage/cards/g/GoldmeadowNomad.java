package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.KithkinGreenWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GoldmeadowNomad extends CardImpl {

    public GoldmeadowNomad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}");

        this.subtype.add(SubType.KITHKIN);
        this.subtype.add(SubType.SCOUT);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // {W}, Exile this card from your graveyard: Create a 1/1 green and white Kithkin creature token. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.GRAVEYARD, new CreateTokenEffect(new KithkinGreenWhiteToken()), new ManaCostsImpl<>("{W}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private GoldmeadowNomad(final GoldmeadowNomad card) {
        super(card);
    }

    @Override
    public GoldmeadowNomad copy() {
        return new GoldmeadowNomad(this);
    }
}
