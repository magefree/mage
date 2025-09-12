package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.common.AttacksAttachedTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.SourceMatchesFilterCondition;
import mage.abilities.costs.mana.ManaCostsImpl;
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
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WritOfPassage extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("its power is 2 or less");
    private static final FilterPermanent filter2 = new FilterCreaturePermanent("creature with power 2 or less");

    static {
        filter.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
        filter2.add(new PowerPredicate(ComparisonType.FEWER_THAN, 3));
    }

    private static final Condition condition = new SourceMatchesFilterCondition(filter);

    public WritOfPassage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.AURA);

        // Enchant creature
        TargetPermanent auraTarget = new TargetCreaturePermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.BoostCreature));
        this.addAbility(new EnchantAbility(auraTarget));

        // Whenever enchanted creature attacks, if its power is 2 or less, it's unblockable this turn.
        this.addAbility(new AttacksAttachedTriggeredAbility(
                new CantBeBlockedTargetEffect().setText("it can't be blocked this turn"),
                AttachmentType.AURA, false, SetTargetPointer.PERMANENT
        ).withInterveningIf(condition));

        // Forecast - {1}{U}, Reveal Writ of Passage from your hand: Target creature with power 2 or less is unblockable this turn.
        Ability ability = new ForecastAbility(new CantBeBlockedTargetEffect(), new ManaCostsImpl<>("{1}{U}"));
        ability.addTarget(new TargetPermanent(filter2));
        this.addAbility(ability);
    }

    private WritOfPassage(final WritOfPassage card) {
        super(card);
    }

    @Override
    public WritOfPassage copy() {
        return new WritOfPassage(this);
    }
}
