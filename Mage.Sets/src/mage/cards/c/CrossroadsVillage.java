package mage.cards.c;

import mage.abilities.common.EntersBattlefieldTappedAsItEntersChooseColorAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.AddManaChosenColorEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CrossroadsVillage extends CardImpl {

    public CrossroadsVillage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.TOWN);

        // This land enters tapped. As it enters, choose a color.
        this.addAbility(new EntersBattlefieldTappedAsItEntersChooseColorAbility());

        // {T}: Add one mana of the chosen color.
        this.addAbility(new SimpleManaAbility(new AddManaChosenColorEffect(), new TapSourceCost()));
    }

    private CrossroadsVillage(final CrossroadsVillage card) {
        super(card);
    }

    @Override
    public CrossroadsVillage copy() {
        return new CrossroadsVillage(this);
    }
}
