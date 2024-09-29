package mage.cards.s;

import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceIsRingBearerCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTargets;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SauronTheNecromancer extends CardImpl {

    public SauronTheNecromancer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Menace
        this.addAbility(new MenaceAbility(false));

        // Whenever Sauron, the Necromancer attacks, exile target creature card from your graveyard. Create a tapped and attacking token that's a copy of that card, except it's a 3/3 black Wraith with menace. At the beginning of the next end step, exile that token unless Sauron is your Ring-bearer.
        Ability ability = new AttacksTriggeredAbility(new SauronTheNecromancerEffect());
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD));
        this.addAbility(ability);
    }

    private SauronTheNecromancer(final SauronTheNecromancer card) {
        super(card);
    }

    @Override
    public SauronTheNecromancer copy() {
        return new SauronTheNecromancer(this);
    }
}

class SauronTheNecromancerEffect extends OneShotEffect {

    private static final Condition condition = new InvertCondition(SourceIsRingBearerCondition.instance);

    SauronTheNecromancerEffect() {
        super(Outcome.Benefit);
        staticText = "exile target creature card from your graveyard. Create a tapped and attacking " +
                "token that's a copy of that card, except it's a 3/3 black Wraith with menace. " +
                "At the beginning of the next end step, exile that token unless {this} is your Ring-bearer";
    }

    private SauronTheNecromancerEffect(final SauronTheNecromancerEffect effect) {
        super(effect);
    }

    @Override
    public SauronTheNecromancerEffect copy() {
        return new SauronTheNecromancerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.EXILED, source, game);
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                null, null, false, 1, true, true
        );
        effect.setPermanentModifier(((token) -> {
            token.setColor(ObjectColor.BLACK);
            token.addSubType(SubType.WRAITH);
            token.setPower(3);
            token.setToughness(3);
            token.addAbility(new MenaceAbility(false));
        }));
        effect.apply(game, source);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new ConditionalOneShotEffect(
                        new ExileTargetEffect(), condition,
                        "exile that token unless {this} is your Ring-bearer"
                ).setTargetPointer(new FixedTargets(effect.getAddedPermanents(), game))
        ), source);
        return true;
    }
}
