package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleEvasionAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.combat.CantBeBlockedByCreaturesSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.ForetellAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.permanent.token.FishNoAbilityToken;
import mage.game.permanent.token.TreasureToken;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class FrostFairLureFish extends CardImpl {

    private static final FilterControlledPermanent filter =
            new FilterControlledPermanent(SubType.FISH, "Fish");
    private static final FilterCreaturePermanent filterHumans =
            new FilterCreaturePermanent(SubType.HUMAN, "Humans");

    public FrostFairLureFish(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{U}{R}");

        this.subtype.add(SubType.FISH);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // When Frost Fair Lure Fish enters the battlefield, create two 1/1 blue Fish creature tokens and two tapped Treasure tokens.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new FishNoAbilityToken(), 2));
        ability.addEffect(new CreateTokenEffect(new TreasureToken(), 2, true)
                .setText("and two tapped Treasure tokens"));
        this.addAbility(ability);

        // Fish you control have haste and can't be blocked by Humans.
        ability = new SimpleStaticAbility(new GainAbilityControlledEffect(
                HasteAbility.getInstance(),
                Duration.WhileOnBattlefield,
                filter
        ));
        ability.addEffect(new GainAbilityControlledEffect(
                new SimpleEvasionAbility(new CantBeBlockedByCreaturesSourceEffect(filterHumans, Duration.WhileOnBattlefield)),
                Duration.WhileOnBattlefield,
                filter
        ).setText("and can't be blocked by Humans"));
        this.addAbility(ability);

        // Foretell {3}{U}{R}
        this.addAbility(new ForetellAbility(this, "{3}{U}{R}"));
    }

    private FrostFairLureFish(final FrostFairLureFish card) {
        super(card);
    }

    @Override
    public FrostFairLureFish copy() {
        return new FrostFairLureFish(this);
    }
}
