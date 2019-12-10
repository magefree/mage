/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.cards;

import mage.abilities.SpellAbility;
import mage.abilities.effects.common.ExileAdventureSpellEffect;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;

import java.util.List;
import java.util.UUID;

/**
 *
 * @author phulin
 */
public class AdventureCardSpellImpl extends CardImpl implements AdventureCardSpell {

    private AdventureCard adventureCardParent;

    public AdventureCardSpellImpl(UUID ownerId, CardSetInfo setInfo, CardType[] cardTypes, String costs, AdventureCard adventureCardParent) {
        super(ownerId, setInfo, cardTypes, costs, SpellAbilityType.ADVENTURE_SPELL);
        this.subtype.add(SubType.ADVENTURE);
        this.replaceSpellAbility(new AdventureCardSpellAbility(getSpellAbility()));
        this.adventureCardParent = adventureCardParent;
    }

    public AdventureCardSpellImpl(final AdventureCardSpellImpl card) {
        super(card);
        this.adventureCardParent = card.adventureCardParent;
    }

    @Override
    public UUID getOwnerId() {
        return adventureCardParent.getOwnerId();
    }

    @Override
    public String getImageName() {
        return adventureCardParent.getImageName();
    }

    @Override
    public String getExpansionSetCode() {
        return adventureCardParent.getExpansionSetCode();
    }

    @Override
    public String getCardNumber() {
        return adventureCardParent.getCardNumber();
    }

    @Override
    public boolean moveToZone(Zone toZone, UUID sourceId, Game game, boolean flag, List<UUID> appliedEffects) {
        return adventureCardParent.moveToZone(toZone, sourceId, game, flag, appliedEffects);
    }

    @Override
    public boolean moveToExile(UUID exileId, String name, UUID sourceId, Game game, List<UUID> appliedEffects) {
        return adventureCardParent.moveToExile(exileId, name, sourceId, game, appliedEffects);
    }

    @Override
    public AdventureCard getMainCard() {
        return adventureCardParent;
    }

    @Override
    public void setZone(Zone zone, Game game) {
        game.setZone(adventureCardParent.getId(), zone);
        game.setZone(adventureCardParent.getSpellCard().getId(), zone);
    }

    @Override
    public AdventureCardSpell copy() {
        return new AdventureCardSpellImpl(this);
    }

    @Override
    public void setParentCard(AdventureCard card) {
        this.adventureCardParent = card;
    }

    @Override
    public AdventureCard getParentCard() {
        return this.adventureCardParent;
    }
}

class AdventureCardSpellAbility extends SpellAbility {
    public AdventureCardSpellAbility(SpellAbility ability) {
        super(ability);
    }

    @Override
    public ActivationStatus canActivate(UUID playerId, Game game) {
        ExileZone adventureExileZone = game.getExile().getExileZone(ExileAdventureSpellEffect.adventureExileId(playerId, game));
        if (adventureExileZone != null && adventureExileZone.contains(this.getSourceId())) {
            return ActivationStatus.getFalse();
        } else {
            return super.canActivate(playerId, game);
        }
    }
}