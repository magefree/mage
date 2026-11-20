package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.ExileSourceFromGraveCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.permanent.token.OfficerToken;

import java.util.UUID;

/**
 * @author muz
 */
public final class PhotosyntheticPhysician extends CardImpl {

    public PhotosyntheticPhysician(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.DOCTOR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // {G}, Exile this card from your graveyard: Create a 1/1 red Officer creature token. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
            Zone.GRAVEYARD,
            new CreateTokenEffect(new OfficerToken()),
            new ManaCostsImpl<>("{G}")
        );
        ability.addCost(new ExileSourceFromGraveCost());
        this.addAbility(ability);
    }

    private PhotosyntheticPhysician(final PhotosyntheticPhysician card) {
        super(card);
    }

    @Override
    public PhotosyntheticPhysician copy() {
        return new PhotosyntheticPhysician(this);
    }
}
