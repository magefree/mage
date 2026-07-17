package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsMoreCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.BecomePreparedSourceEffect;
import mage.abilities.effects.common.CreateTokenTargetEffect;
import mage.abilities.effects.common.ExileAndGainLifeEqualPowerTargetEffect;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.Inkling11Token;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class EmeritusOfTruce extends PrepareCard {

    private static final Condition condition = new OpponentControlsMoreCondition(StaticFilters.FILTER_PERMANENT_CREATURES);

    public EmeritusOfTruce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}", "Swords to Plowshares", new CardType[]{CardType.INSTANT},"{W}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When this creature enters, target player creates a 1/1 white and black Inkling creature token with flying. Then if an opponent controls more creatures than you, this creature becomes prepared.
        Ability ability = new EntersBattlefieldTriggeredAbility(new CreateTokenTargetEffect(new Inkling11Token()));
        ability.addEffect(new ConditionalOneShotEffect(
            new BecomePreparedSourceEffect(),
            condition
        ).setText("Then if an opponent controls more creatures than you, this creature becomes prepared"));
        ability.addTarget(new TargetPlayer());
        this.addAbility(ability);

        // Swords to Plowshares
        // Instant {W}
        // Exile target creature. Its controller gains life equal to its power.
        this.getSpellCard().getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellCard().getSpellAbility().addEffect(new ExileAndGainLifeEqualPowerTargetEffect());
    }

    private EmeritusOfTruce(final EmeritusOfTruce card) {
        super(card);
    }

    @Override
    public EmeritusOfTruce copy() {
        return new EmeritusOfTruce(this);
    }
}
