package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.abilities.effects.common.continuous.GainAnchorWordAbilitySourceEffect;
import mage.abilities.effects.common.ruleModifying.PlayFromGraveyardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ModeChoice;
import mage.filter.StaticFilters;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author androosss
 */
public final class GlacierwoodSiege extends CardImpl {

    public GlacierwoodSiege(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{G}{U}");

        // As this enchantment enters, choose Temur or Sultai.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseModeEffect(ModeChoice.TEMUR, ModeChoice.SULTAI)));

        // * Temur -- Whenever you cast an instant or sorcery spell, target player mills four cards.
        Ability ability = new SpellCastControllerTriggeredAbility(
                new MillCardsTargetEffect(4),
                StaticFilters.FILTER_SPELL_AN_INSTANT_OR_SORCERY, false
        );
        ability.addTarget(new TargetPlayer());
        this.addAbility(new SimpleStaticAbility(new GainAnchorWordAbilitySourceEffect(ability, ModeChoice.TEMUR)));

        // * Sultai -- You may play lands from your graveyard.
        this.addAbility(new SimpleStaticAbility(new GainAnchorWordAbilitySourceEffect(
                PlayFromGraveyardControllerEffect.playLands(), ModeChoice.SULTAI
        )));
    }

    private GlacierwoodSiege(final GlacierwoodSiege card) {
        super(card);
    }

    @Override
    public GlacierwoodSiege copy() {
        return new GlacierwoodSiege(this);
    }
}
