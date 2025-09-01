package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.condition.common.VoidCondition;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.effects.common.SacrificeSourceUnlessConditionEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInGraveyard;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.watchers.common.VoidWatcher;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ChoraleOfTheVoid extends CardImpl {

    private static final FilterCard filter = new FilterCreatureCard("creature card from defending player's graveyard");

    static {
        filter.add(ChoraleOfTheVoidPredicate.instance);
    }

    public ChoraleOfTheVoid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{B}");

        this.subtype.add(SubType.AURA);

        // Enchant creature you control
        TargetPermanent auraTarget = new TargetControlledCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Whenever enchanted creature attacks, put target creature card from defending player's graveyard onto the battlefield under your control tapped and attacking.
        Ability ability = new AttacksAttachedTriggeredAbility(
                new ReturnFromGraveyardToBattlefieldTargetEffect(true, true),
                AttachmentType.AURA, false
        );
        ability.addTarget(new TargetCardInGraveyard(filter));
        this.addAbility(ability);

        // Void -- At the beginning of your end step, sacrifice this Aura unless a nonland permanent left the battlefield this turn or a spell was warped this turn.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new SacrificeSourceUnlessConditionEffect(VoidCondition.instance)
        ).setAbilityWord(AbilityWord.VOID).addHint(VoidCondition.getHint()), new VoidWatcher());
    }

    private ChoraleOfTheVoid(final ChoraleOfTheVoid card) {
        super(card);
    }

    @Override
    public ChoraleOfTheVoid copy() {
        return new ChoraleOfTheVoid(this);
    }
}

enum ChoraleOfTheVoidPredicate implements ObjectSourcePlayerPredicate<Card> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Card> input, Game game) {
        return Optional
                .ofNullable(input)
                .map(ObjectSourcePlayer::getSource)
                .map(source -> source.getSourcePermanentOrLKI(game))
                .map(Permanent::getAttachedTo)
                .map(attachmentId -> game.getCombat().getDefendingPlayerId(attachmentId, game))
                .filter(playerId -> input.getObject().isOwnedBy(playerId))
                .isPresent();
    }
}
