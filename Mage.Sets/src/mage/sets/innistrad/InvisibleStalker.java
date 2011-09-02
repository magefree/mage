package mage.sets.innistrad;

import mage.Constants;
import mage.MageInt;
import mage.abilities.keyword.HexproofAbility;
import mage.abilities.keyword.UnblockableAbility;
import mage.cards.CardImpl;

import java.util.UUID;

public class InvisibleStalker extends CardImpl<InvisibleStalker> {
    public InvisibleStalker(UUID ownerId) {
        super(ownerId, 60, "Invisible Stalker", Constants.Rarity.UNCOMMON, new Constants.CardType[]{Constants.CardType.CREATURE}, "{1}{U}");
        this.expansionSetCode = "ISD";
		this.subtype.add("Human");
        this.subtype.add("Rogue");
		this.color.setBlue(true);
		this.power = new MageInt(1);
		this.toughness = new MageInt(1);

        this.addAbility(new HexproofAbility());
        this.addAbility(UnblockableAbility.getInstance());

    }

    @Override
    public InvisibleStalker copy() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
