package mage.cards.y;

import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.CantCastMoreThanOneSpellEffect;
import mage.abilities.effects.common.replacement.GraveyardFromAnywhereExileReplacementEffect;
import mage.abilities.effects.common.ruleModifying.PlayFromGraveyardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.TargetController;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class YawgmothsAgenda extends CardImpl {

    public YawgmothsAgenda(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{B}{B}");

        // You can't cast more than one spell each turn.
        this.addAbility(new SimpleStaticAbility(new CantCastMoreThanOneSpellEffect(TargetController.YOU)));

        // You may play lands and cast spells from your graveyard.
        this.addAbility(new SimpleStaticAbility(PlayFromGraveyardControllerEffect.playLandsAndCastSpells(Duration.WhileOnBattlefield)));

        // If a card would be put into your graveyard from anywhere, exile it instead.
        this.addAbility(new SimpleStaticAbility(new GraveyardFromAnywhereExileReplacementEffect(true, false)));
    }

    private YawgmothsAgenda(final YawgmothsAgenda card) {
        super(card);
    }

    @Override
    public YawgmothsAgenda copy() {
        return new YawgmothsAgenda(this);
    }
}
