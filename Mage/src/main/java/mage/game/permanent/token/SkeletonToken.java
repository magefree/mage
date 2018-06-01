package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Zone;

public final class SkeletonToken extends TokenImpl {

    public SkeletonToken() {
        super("Skeleton", "1/1 black Skeleton creature with \"{B}: Regenerate this creature\"");
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.SKELETON);
        color.setBlack(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ColoredManaCost(ColoredManaSymbol.B)));
    }

    public SkeletonToken(final SkeletonToken token) {
        super(token);
    }

    public SkeletonToken copy() {
        return new SkeletonToken(this);
    }
}
