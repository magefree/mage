package mage.cards.h;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CantLoseGameSourceControllerEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeraldOfEternalDawn extends CardImpl {

    public HeraldOfEternalDawn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}{W}");

        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // You can't lose the game and your opponents can't win the game.
        this.addAbility(new SimpleStaticAbility(new CantLoseGameSourceControllerEffect()));
    }

    private HeraldOfEternalDawn(final HeraldOfEternalDawn card) {
        super(card);
    }

    @Override
    public HeraldOfEternalDawn copy() {
        return new HeraldOfEternalDawn(this);
    }
}
