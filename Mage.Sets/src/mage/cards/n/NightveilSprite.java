package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class NightveilSprite extends CardImpl {

    public NightveilSprite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Nightveil Faerie attacks, surveil 1.
        this.addAbility(new AttacksTriggeredAbility(new SurveilEffect(1), false));
    }

    private NightveilSprite(final NightveilSprite card) {
        super(card);
    }

    @Override
    public NightveilSprite copy() {
        return new NightveilSprite(this);
    }
}
