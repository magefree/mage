package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class DreamBeavers extends CardImpl {

    public DreamBeavers(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.BEAVER);
        this.subtype.add(SubType.NIGHTMARE);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters, each opponent loses 1 life and you gain 1 life. Scry 1.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeOpponentsEffect(1));
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        ability.addEffect(new ScryEffect(1));
        this.addAbility(ability);
    }

    private DreamBeavers(final DreamBeavers card) {
        super(card);
    }

    @Override
    public DreamBeavers copy() {
        return new DreamBeavers(this);
    }
}
