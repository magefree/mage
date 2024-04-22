package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledPermanent;

import java.util.UUID;

/**
 * @author nomage
 */
public final class BladewingsThrall extends CardImpl {

    private static final String RULE = "{this} has flying as long as you control a Dragon";

    public BladewingsThrall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Bladewing's Thrall has flying as long as you control a Dragon.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(FlyingAbility.getInstance(), Duration.WhileOnBattlefield),
                new PermanentsOnTheBattlefieldCondition(new FilterControlledPermanent(SubType.DRAGON, "a Dragon")),
                RULE)));

        // When a Dragon enters the battlefield, you may return Bladewing's Thrall from your graveyard to the battlefield.
        this.addAbility(new EntersBattlefieldAllTriggeredAbility(Zone.GRAVEYARD,
                new ReturnSourceFromGraveyardToBattlefieldEffect(), new FilterPermanent(SubType.DRAGON, "a Dragon"), true)
                .setTriggerPhrase("When a Dragon enters the battlefield, "));
    }

    private BladewingsThrall(final BladewingsThrall card) {
        super(card);
    }

    @Override
    public BladewingsThrall copy() {
        return new BladewingsThrall(this);
    }
}
