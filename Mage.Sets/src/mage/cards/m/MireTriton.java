package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MireTriton extends CardImpl {

    public MireTriton(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.MERFOLK);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // When Mire Triton enters the battlefield, put the top two cards of your library into your graveyard and you gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new MillCardsControllerEffect(2)
        );
        ability.addEffect(new GainLifeEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private MireTriton(final MireTriton card) {
        super(card);
    }

    @Override
    public MireTriton copy() {
        return new MireTriton(this);
    }
}
