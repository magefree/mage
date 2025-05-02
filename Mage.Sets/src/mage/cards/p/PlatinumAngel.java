package mage.cards.p;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CantLoseGameSourceControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class PlatinumAngel extends CardImpl {

    public PlatinumAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{7}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        this.addAbility(FlyingAbility.getInstance());
        this.addAbility(new SimpleStaticAbility(new CantLoseGameSourceControllerEffect()));
    }

    private PlatinumAngel(final PlatinumAngel card) {
        super(card);
    }

    @Override
    public PlatinumAngel copy() {
        return new PlatinumAngel(this);
    }
}
