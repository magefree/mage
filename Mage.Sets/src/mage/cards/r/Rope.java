package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.effects.common.continuous.GainAbilityAttachedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Cguy7777
 */
public final class Rope extends CardImpl {

    public Rope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{G}");

        this.subtype.add(SubType.CLUE);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +1/+2, has reach, and can't be blocked by more than one creature.
        Ability boostAbility = new SimpleStaticAbility(new BoostEquippedEffect(1, 2));
        boostAbility.addEffect(new GainAbilityAttachedEffect(ReachAbility.getInstance(), AttachmentType.EQUIPMENT)
                .setText(", has reach"));
        boostAbility.addEffect(new CantBeBlockedByMoreThanOneAttachedEffect(AttachmentType.EQUIPMENT, 1)
                .setText(", and can't be blocked by more than one creature"));
        this.addAbility(boostAbility);

        // {2}, Sacrifice Rope: Draw a card.
        Ability drawAbility = new SimpleActivatedAbility(new DrawCardSourceControllerEffect(1), new GenericManaCost(2));
        drawAbility.addCost(new SacrificeSourceCost());
        this.addAbility(drawAbility);

        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3), false));
    }

    private Rope(final Rope card) {
        super(card);
    }

    @Override
    public Rope copy() {
        return new Rope(this);
    }
}

// Copied from Alpha Authority
class CantBeBlockedByMoreThanOneAttachedEffect extends ContinuousEffectImpl {

    protected int amount;
    protected AttachmentType attachmentType;

    public CantBeBlockedByMoreThanOneAttachedEffect(AttachmentType attachmentType, int amount) {
        this(attachmentType, amount, Duration.WhileOnBattlefield);
    }

    public CantBeBlockedByMoreThanOneAttachedEffect(AttachmentType attachmentType, int amount, Duration duration) {
        super(duration, Outcome.Benefit);
        this.amount = amount;
        this.attachmentType = attachmentType;
        staticText = attachmentType.verb() + " creature can't be blocked by more than " + CardUtil.numberToText(amount) + " creature" + (amount == 1 ? "" : "s");
    }

    private CantBeBlockedByMoreThanOneAttachedEffect(final CantBeBlockedByMoreThanOneAttachedEffect effect) {
        super(effect);
        this.amount = effect.amount;
        this.attachmentType = effect.attachmentType;
    }

    @Override
    public CantBeBlockedByMoreThanOneAttachedEffect copy() {
        return new CantBeBlockedByMoreThanOneAttachedEffect(this);
    }

    @Override
    public boolean apply(Layer layer, SubLayer sublayer, Ability source, Game game) {
        switch (layer) {
            case RulesEffects:
                Permanent attachment = game.getPermanent(source.getSourceId());
                if (attachment != null && attachment.getAttachedTo() != null) {
                    Permanent perm = game.getPermanent(attachment.getAttachedTo());
                    if (perm != null) {
                        perm.setMaxBlockedBy(amount);
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean hasLayer(Layer layer) {
        return layer == Layer.RulesEffects;
    }
}
