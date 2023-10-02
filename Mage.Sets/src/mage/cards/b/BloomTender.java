package mage.cards.b;

import mage.MageInt;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class BloomTender extends CardImpl {

    public BloomTender(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.ELF, SubType.DRUID);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {T}: For each color among permanents you control, add one mana of that color.
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, new BloomTenderEffect(), new TapSourceCost()));

    }

    private BloomTender(final BloomTender card) {
        super(card);
    }

    @Override
    public BloomTender copy() {
        return new BloomTender(this);
    }
}

class BloomTenderEffect extends ManaEffect {

    public BloomTenderEffect() {
        super();
        staticText = "For each color among permanents you control, add one mana of that color";
    }

    private BloomTenderEffect(final BloomTenderEffect effect) {
        super(effect);
    }

    @Override
    public BloomTenderEffect copy() {
        return new BloomTenderEffect(this);
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        Mana mana = new Mana();
        if (game == null) {
            return mana;
        }
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(source.getControllerId())) {
            if (mana.getBlack() == 0 && permanent.getColor(game).isBlack()) {
                mana.increaseBlack();
            }
            if (mana.getBlue() == 0 && permanent.getColor(game).isBlue()) {
                mana.increaseBlue();
            }
            if (mana.getRed() == 0 && permanent.getColor(game).isRed()) {
                mana.increaseRed();
            }
            if (mana.getGreen() == 0 && permanent.getColor(game).isGreen()) {
                mana.increaseGreen();
            }
            if (mana.getWhite() == 0 && permanent.getColor(game).isWhite()) {
                mana.increaseWhite();
            }
        }
        return mana;
    }
}
