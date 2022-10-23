package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.condition.common.AttachedToMatchesFilterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.keyword.ForecastAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WritOfPassage extends CardImpl {

    public WritOfPassage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever enchanted creature attacks, if its power is 2 or less, it's unblockable this turn.
        FilterPermanent filter = new FilterPermanent("if enchanted creature's power is 2 or less");
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
        ability = new ConditionalInterveningIfTriggeredAbility(new AttacksAttachedTriggeredAbility(
                new WritOfPassageAttachedEffect(AttachmentType.AURA), AttachmentType.AURA, false),
                new AttachedToMatchesFilterCondition(filter), "Whenever enchanted creature attacks, if its power is 2 or less, it can't be blocked this turn.");
        this.addAbility(ability);
        // Forecast - {1}{U}, Reveal Writ of Passage from your hand: Target creature with power 2 or less is unblockable this turn.
        ForecastAbility ability2 = new ForecastAbility(new CantBeBlockedTargetEffect(), new ManaCostsImpl<>("{1}{U}"));
        FilterCreaturePermanent filter2 = new FilterCreaturePermanent("creature with power 2 or less");
        filter2.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
        ability2.addTarget(new TargetCreaturePermanent(filter2));
        this.addAbility(ability2);
    }

    private WritOfPassage(final WritOfPassage card) {
        super(card);
    }

    @Override
    public WritOfPassage copy() {
        return new WritOfPassage(this);
    }
}

class WritOfPassageAttachedEffect extends RestrictionEffect {

    public WritOfPassageAttachedEffect(AttachmentType attachmentType) {
        super(Duration.EndOfTurn);
        this.staticText = attachmentType.verb() + " creature can't be blocked";
    }

    public WritOfPassageAttachedEffect(WritOfPassageAttachedEffect effect) {
        super(effect);
    }

    @Override
    public WritOfPassageAttachedEffect copy() {
        return new WritOfPassageAttachedEffect(this);
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        Permanent attachment = game.getPermanent(source.getSourceId());
        return attachment != null && attachment.isAttachedTo(permanent.getId());
    }
}
