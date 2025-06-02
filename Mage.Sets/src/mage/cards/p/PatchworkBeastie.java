package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.dynamicvalue.common.CardTypesInGraveyardCount;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.DeliriumCondition;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.MillCardsControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PatchworkBeastie extends CardImpl {

    public PatchworkBeastie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Delirium -- Patchwork Beastie can't attack or block unless there are four or more card types among cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(new PatchworkBeastieEffect())
                .setAbilityWord(AbilityWord.DELIRIUM)
                .addHint(CardTypesInGraveyardCount.YOU.getHint()));

        // At the beginning of your upkeep, you may mill a card.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                new MillCardsControllerEffect(1), true
        ));
    }

    private PatchworkBeastie(final PatchworkBeastie card) {
        super(card);
    }

    @Override
    public PatchworkBeastie copy() {
        return new PatchworkBeastie(this);
    }
}

class PatchworkBeastieEffect extends RestrictionEffect {

    PatchworkBeastieEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "{this} can't attack or block unless there are four or more card types among cards in your graveyard";
    }

    private PatchworkBeastieEffect(final PatchworkBeastieEffect effect) {
        super(effect);
    }

    @Override
    public PatchworkBeastieEffect copy() {
        return new PatchworkBeastieEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId())
                && !DeliriumCondition.instance.apply(game, source);
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public boolean canAttack(Game game, boolean canUseChooseDialogs) {
        return false;
    }
}
