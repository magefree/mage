package mage.cards.a;

import mage.MageInt;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.EmergeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.AlienToken;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author notgreat
 */
public final class AdiposeOffspring extends CardImpl {

    public AdiposeOffspring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.subtype.add(SubType.ALIEN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Emerge {5}{W}
        this.addAbility(new EmergeAbility(this, "{5}{W}"));
        // When Adipose Offspring enters the battlefield, create a 2/2 white Alien creature token. If Adipose Offspring's emerge cost was paid, instead create X of those tokens, where X is the sacrificed creature's toughness.
        this.addAbility(new EntersBattlefieldAbility(new CreateTokenEffect(new AlienToken(), AdiposeOffspringValue.instance)
                .setText("create a 2/2 white Alien creature token. If Adipose Offspring's emerge cost was paid, "
                        +"instead create X of those tokens, where X is the sacrificed creature's toughness.")));
    }

    private AdiposeOffspring(final AdiposeOffspring card) {
        super(card);
    }

    @Override
    public AdiposeOffspring copy() {
        return new AdiposeOffspring(this);
    }
}


enum AdiposeOffspringValue implements DynamicValue {
    instance;

    @Override
    public int calculate(Game game, Ability sourceAbility, Effect effect) {
        MageObjectReference blank = new MageObjectReference(new UUID(0,0));
        MageObjectReference mor = CardUtil.getSourceCostsTag(game, sourceAbility, EmergeAbility.EMERGE_ACTIVATION_CREATURE_REFERENCE, blank);
        if (!mor.equals(blank)) {
            Permanent creature = mor.getPermanentOrLKIBattlefield(game);
            if (creature != null) {
                return creature.getToughness().getValue();
            }
        }
        return 1;
    }

    @Override
    public AdiposeOffspringValue copy() {
        return this;
    }

    @Override
    public String getMessage() {
        return "One or the sacrificed creature's toughness";
    }

    @Override
    public String toString() {
        return "1";
    }
}
