package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterArtifactCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KamiOfIndustry extends CardImpl {

    private static final FilterCard filter
            = new FilterArtifactCard("artifact card with mana value 3 or less from your graveyard");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, 4));
    }

    public KamiOfIndustry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.subtype.add(SubType.SPIRIT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // When Kami of Industry enters the battlefield, return target artifact card with mana value 3 or less from your graveyard to the battlefield. It gains haste. Sacrifice it at the beginning of the next end step.
        Ability ability = new EntersBattlefieldTriggeredAbility(new KamiOfIndustryEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);
    }

    private KamiOfIndustry(final KamiOfIndustry card) {
        super(card);
    }

    @Override
    public KamiOfIndustry copy() {
        return new KamiOfIndustry(this);
    }
}

class KamiOfIndustryEffect extends OneShotEffect {

    KamiOfIndustryEffect() {
        super(Outcome.Benefit);
        staticText = "return target artifact card with mana value 3 or less from your graveyard " +
                "to the battlefield. It gains haste. Sacrifice it at the beginning of the next end step";
    }

    private KamiOfIndustryEffect(final KamiOfIndustryEffect effect) {
        super(effect);
    }

    @Override
    public KamiOfIndustryEffect copy() {
        return new KamiOfIndustryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(source.getFirstTarget());
        if (player == null || card == null) {
            return false;
        }
        player.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = game.getPermanent(card.getId());
        if (permanent == null) {
            return false;
        }
        game.addEffect(new GainAbilityTargetEffect(HasteAbility.getInstance())
                .setTargetPointer(new FixedTarget(permanent, game)), source);
        game.addDelayedTriggeredAbility(new AtTheBeginOfNextEndStepDelayedTriggeredAbility(
                new SacrificeTargetEffect("sacrifice it").setTargetPointer(new FixedTarget(permanent, game))
        ), source);
        return true;
    }
}
