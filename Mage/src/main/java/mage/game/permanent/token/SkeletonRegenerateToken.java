package mage.game.permanent.token;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.Arrays;

public final class SkeletonRegenerateToken extends TokenImpl {

    public SkeletonRegenerateToken() {
        super("Skeleton Token", "1/1 black Skeleton creature token with \"{B}: Regenerate this creature\"");
        cardType.add(CardType.CREATURE);
        this.subtype.add(SubType.SKELETON);
        color.setBlack(true);
        power = new MageInt(1);
        toughness = new MageInt(1);
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new RegenerateSourceEffect(), new ColoredManaCost(ColoredManaSymbol.B)));

        availableImageSetCodes = Arrays.asList("ALA", "A25");
    }

    public SkeletonRegenerateToken(final SkeletonRegenerateToken token) {
        super(token);
    }

    public SkeletonRegenerateToken copy() {
        return new SkeletonRegenerateToken(this);
    }
}
