package mage.cards.a;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ApostleOfPurifyingLight extends CardImpl {

    public ApostleOfPurifyingLight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Protection from black
        this.addAbility(ProtectionAbility.from(ObjectColor.BLACK));

        // {2}: Exile target card from a graveyard.
        Ability ability = new SimpleActivatedAbility(new ExileTargetEffect(), new GenericManaCost(2));
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);
    }

    private ApostleOfPurifyingLight(final ApostleOfPurifyingLight card) {
        super(card);
    }

    @Override
    public ApostleOfPurifyingLight copy() {
        return new ApostleOfPurifyingLight(this);
    }
}
