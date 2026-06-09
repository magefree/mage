package mage.cards;

import mage.abilities.Abilities;
import mage.abilities.Ability;
import mage.abilities.SpellAbility;
import mage.constants.CardType;
import mage.constants.SpellAbilityType;
import mage.constants.Zone;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.List;
import java.util.UUID;

/**
 * @author LevelX2
 */
public abstract class SplitCard extends CardWithPartsImpl<SplitCardHalf, SplitCard> {

    public static final String FUSE_RULE = "Fuse <i>(You may cast one or both halves of this card from your hand.)</i>";

    protected SplitCard(UUID ownerId, CardSetInfo setInfo, CardType[] cardTypes, String costsLeft, String costsRight, SpellAbilityType spellAbilityType) {
        this(ownerId, setInfo, cardTypes, cardTypes, costsLeft, costsRight, spellAbilityType);
    }

    protected SplitCard(UUID ownerId, CardSetInfo setInfo, CardType[] typesLeft, CardType[] typesRight, String costsLeft, String costsRight, SpellAbilityType spellAbilityType) {
        super(ownerId, setInfo, CardType.mergeTypes(typesLeft, typesRight), costsLeft + costsRight, spellAbilityType);
        String[] names = setInfo.getName().split(" // ");
        leftHalfCard = new SplitCardHalfImpl(this.getOwnerId(), new CardSetInfo(names[0], setInfo.getExpansionSetCode(), setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()), typesLeft, costsLeft, this, SpellAbilityType.SPLIT_LEFT);
        rightHalfCard = new SplitCardHalfImpl(this.getOwnerId(), new CardSetInfo(names[1], setInfo.getExpansionSetCode(), setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()), typesRight, costsRight, this, SpellAbilityType.SPLIT_RIGHT);
    }

    // Params reordered as we need the same arguments as the parent constructor, with slightly different behaviour.
    // Currently only used for rooms, because they are the only current split card with a shared type line.
    protected SplitCard(UUID ownerId, CardSetInfo setInfo, String costsLeft, String costsRight, SpellAbilityType spellAbilityType, CardType[] singleTypeLine) {
        super(ownerId, setInfo, singleTypeLine, costsLeft + costsRight, spellAbilityType);
        String[] names = setInfo.getName().split(" // ");
        leftHalfCard = new SplitCardHalfImpl(this.getOwnerId(), new CardSetInfo(names[0], setInfo.getExpansionSetCode(), setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()), singleTypeLine, costsLeft, this, SpellAbilityType.SPLIT_LEFT);
        rightHalfCard = new SplitCardHalfImpl(this.getOwnerId(), new CardSetInfo(names[1], setInfo.getExpansionSetCode(), setInfo.getCardNumber(), setInfo.getRarity(), setInfo.getGraphicInfo()), singleTypeLine, costsRight, this, SpellAbilityType.SPLIT_RIGHT);
    }

    protected SplitCard(SplitCard card) {
        super(card);
    }

    @Override
    public boolean cast(Game game, Zone fromZone, SpellAbility ability, UUID controllerId) {
        switch (ability.getSpellAbilityType()) {
            case SPLIT_LEFT:
                return this.getLeftHalfCard().cast(game, fromZone, ability, controllerId);
            case SPLIT_RIGHT:
                return this.getRightHalfCard().cast(game, fromZone, ability, controllerId);
            default:
                this.getLeftHalfCard().getSpellAbility().setControllerId(controllerId);
                this.getRightHalfCard().getSpellAbility().setControllerId(controllerId);
                return super.cast(game, fromZone, ability, controllerId);
        }
    }

    @Override
    public List<String> getRules() {
        Abilities<Ability> sourceAbilities = this.getAbilities();
        List<String> res = CardUtil.getCardRulesWithAdditionalInfo(
                this,
                sourceAbilities,
                sourceAbilities
        );
        if (getSpellAbility().getSpellAbilityType() == SpellAbilityType.SPLIT_FUSED) {
            res.add("--------------------------------------------------------------------------\n" + FUSE_RULE);
        }
        return res;
    }

    @Override
    public List<String> getRules(Game game) {
        Abilities<Ability> sourceAbilities = this.getAbilities(game);
        List<String> res = CardUtil.getCardRulesWithAdditionalInfo(
                game,
                this,
                sourceAbilities,
                sourceAbilities
        );
        if (getSpellAbility().getSpellAbilityType() == SpellAbilityType.SPLIT_FUSED) {
            res.add("--------------------------------------------------------------------------\n" + FUSE_RULE);
        }
        return res;
    }


    @Override
    public int getManaValue() {
        // 202.3d The converted mana cost of a split card not on the stack or of a fused split spell on the
        // stack is determined from the combined mana costs of its halves. Otherwise, while a split card is
        // on the stack, the converted mana cost of the spell is determined by the mana cost of the half
        // that was chosen to be cast. See rule 708, “Split Cards.”

        // split card and it's halfes contains own mana costs, so no need to rewrite logic
        return super.getManaValue();
    }

    @Override
    public UUID getIdForBattlefield(Game game, Ability source) {
        return null;
    }
}
