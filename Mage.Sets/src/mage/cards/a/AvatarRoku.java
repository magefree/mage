package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.FirebendingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.DragonFirebendingToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AvatarRoku extends CardImpl {

    public AvatarRoku(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.nightCard = true;

        // Firebending 4
        this.addAbility(new FirebendingAbility(4));

        // {8}: Create a 4/4 red Dragon creature token with flying and firebending 4.
        this.addAbility(new SimpleActivatedAbility(
                new CreateTokenEffect(new DragonFirebendingToken()), new GenericManaCost(8)
        ));
    }

    private AvatarRoku(final AvatarRoku card) {
        super(card);
    }

    @Override
    public AvatarRoku copy() {
        return new AvatarRoku(this);
    }
}
