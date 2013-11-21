/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */
package mage.sets.commander2013;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.AttachedToTappedCondition;
import mage.abilities.condition.common.EquipmentAttachedCondition;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostControlledEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class SwordOfTheParuns extends CardImpl<SwordOfTheParuns> {

    private static final FilterCreaturePermanent filterTapped = new FilterCreaturePermanent();
    private static final FilterCreaturePermanent filterUntapped = new FilterCreaturePermanent();
    static {
        filterTapped.add(new TappedPredicate());
        filterUntapped.add(Predicates.not(new TappedPredicate()));
    }

    public SwordOfTheParuns(UUID ownerId) {
        super(ownerId, 264, "Sword of the Paruns", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "C13";
        this.subtype.add("Equipment");

        // As long as equipped creature is tapped, tapped creatures you control get +2/+0.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinousEffect(
                new BoostControlledEffect(2,0, Duration.WhileOnBattlefield, filterTapped),
                new CompoundCondition(EquipmentAttachedCondition.getInstance(), new AttachedToTappedCondition()),
                "As long as equipped creature is tapped, tapped creatures you control get +2/+0"
        )));

        // As long as equipped creature is untapped, untapped creatures you control get +0/+2.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ConditionalContinousEffect(
                new BoostControlledEffect(0,2, Duration.WhileOnBattlefield, filterUntapped),
                new CompoundCondition(EquipmentAttachedCondition.getInstance(), new InvertCondition(new AttachedToTappedCondition())),
                "As long as equipped creature is untapped, untapped creatures you control get +0/+2"
        )));

        // {3}: You may tap or untap equipped creature.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new MayTapOrUntapAttachedEffect(), new GenericManaCost(3)));
        // Equip {3}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3)));
    }

    public SwordOfTheParuns(final SwordOfTheParuns card) {
        super(card);
    }

    @Override
    public SwordOfTheParuns copy() {
        return new SwordOfTheParuns(this);
    }
}

class MayTapOrUntapAttachedEffect extends OneShotEffect<MayTapOrUntapAttachedEffect> {

    public MayTapOrUntapAttachedEffect() {
        super(Outcome.Benefit);
        this.staticText = "You may tap or untap equipped creature.";
    }

    public MayTapOrUntapAttachedEffect(final MayTapOrUntapAttachedEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = game.getPermanent(source.getSourceId());
        if (equipment == null) {
            equipment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent equipedCreature = game.getPermanent(equipment.getAttachedTo());
            Player player = game.getPlayer(source.getControllerId());
            if (equipedCreature != null && player != null) {
                if (equipedCreature.isTapped()) {
                    if (player.chooseUse(Outcome.Untap, "Untap equipped creature?", game)) {
                        equipedCreature.untap(game);
                    }
                } else {
                    if (player.chooseUse(Outcome.Tap, "Tap equipped creature?", game)) {
                        equipedCreature.tap(game);
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public MayTapOrUntapAttachedEffect copy() {
        return new MayTapOrUntapAttachedEffect(this);
    }
}
