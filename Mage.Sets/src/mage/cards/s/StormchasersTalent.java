package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.common.BecomesClassLevelTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.abilities.effects.common.continuous.GainClassAbilitySourceEffect;
import mage.abilities.keyword.ClassLevelAbility;
import mage.abilities.keyword.ClassReminderAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.OtterProwessToken;
import mage.target.common.TargetCardInYourGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StormchasersTalent extends CardImpl {

    public StormchasersTalent(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{U}");

        this.subtype.add(SubType.CLASS);

        // (Gain the next level as a sorcery to add its ability.)
        this.addAbility(new ClassReminderAbility());

        // When Stormchaser's Talent enters, create a 1/1 blue and red Otter creature token with prowess.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new OtterProwessToken())));

        // {3}{U}: Level 2
        this.addAbility(new ClassLevelAbility(2, "{3}{U}"));

        // When this Class becomes level 2, return target instant or sorcery card from your graveyard to your hand.
        Ability ability = new BecomesClassLevelTriggeredAbility(new ReturnFromGraveyardToHandTargetEffect(), 2);
        ability.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));
        this.addAbility(ability);

        // {5}{U}: Level 3
        this.addAbility(new ClassLevelAbility(3, "{5}{U}"));

        // Whenever you cast an instant or sorcery spell, create a 1/1 blue and red Otter creature token with prowess.
        this.addAbility(new SimpleStaticAbility(new GainClassAbilitySourceEffect(
                new SpellCastControllerTriggeredAbility(
                        new CreateTokenEffect(new OtterProwessToken()),
                        StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
                ), 3
        )));
    }

    private StormchasersTalent(final StormchasersTalent card) {
        super(card);
    }

    @Override
    public StormchasersTalent copy() {
        return new StormchasersTalent(this);
    }
}
