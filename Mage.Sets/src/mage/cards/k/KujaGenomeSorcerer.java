package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.common.FilterControlledPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.token.BlackWizardToken;
import mage.util.CardUtil;

import java.util.Optional;
import java.util.UUID;

/**
 * @author balazskristof
 */
public final class KujaGenomeSorcerer extends TransformingDoubleFacedCard {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.WIZARD);
    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter, ComparisonType.OR_GREATER, 4);
    private static final Hint hint = new ValueHint("Wizards you control", new PermanentsOnBattlefieldCount(filter));

    public KujaGenomeSorcerer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.MUTANT, SubType.WIZARD}, "{2}{B}{R}",
                "Trance Kuja, Fate Defied",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.AVATAR, SubType.WIZARD}, "BR");

        // Kuja, Genome Sorcerer
        this.getLeftHalfCard().setPT(3, 4);

        // At the beginning of your end step, create a tapped 0/1 black Wizard creature token with "Whenever you cast a noncreature spell, this token deals 1 damage to each opponent.", Then if you control four or more Wizards, transform Kuja.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new BlackWizardToken(), 1, true)
        );
        ability.addEffect(new ConditionalOneShotEffect(
                new TransformSourceEffect(),
                condition,
                "if you control four or more Wizards, transform {this}"
        ).concatBy("Then"));
        ability.addHint(hint);
        this.getLeftHalfCard().addAbility(ability);

        // Trance Kuja, Fate Defied
        this.getRightHalfCard().setPT(4, 6);

        // Flame Star -- If a Wizard you control would deal damage to a permanent or player, it deals double that damage instead.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new TranceKujaFateDefiedEffect()).withFlavorWord("Flare Star"));
    }

    private KujaGenomeSorcerer(final KujaGenomeSorcerer card) {
        super(card);
    }

    @Override
    public KujaGenomeSorcerer copy() {
        return new KujaGenomeSorcerer(this);
    }
}

class TranceKujaFateDefiedEffect extends ReplacementEffectImpl {

    TranceKujaFateDefiedEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a Wizard you control would deal damage to a permanent or player, it deals double that damage instead.";
    }

    private TranceKujaFateDefiedEffect(final TranceKujaFateDefiedEffect effect) {
        super(effect);
    }

    @Override
    public TranceKujaFateDefiedEffect copy() {
        return new TranceKujaFateDefiedEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType().equals(GameEvent.EventType.DAMAGE_PLAYER)
                || event.getType().equals(GameEvent.EventType.DAMAGE_PERMANENT);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return game.getControllerId(event.getSourceId()).equals(source.getControllerId())
                && Optional.ofNullable(game.getObject(event.getSourceId()))
                .map(object -> object.hasSubtype(SubType.WIZARD, game))
                .orElse(false);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(CardUtil.overflowMultiply(event.getAmount(), 2));
        return false;
    }
}