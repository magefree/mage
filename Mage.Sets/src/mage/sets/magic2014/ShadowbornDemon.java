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
package mage.sets.magic2014;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.common.UnlessCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.filter.common.FilterCreatureCard;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class ShadowbornDemon extends CardImpl<ShadowbornDemon> {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Demon creature");
    static {
        filter.add(Predicates.not(new SubtypePredicate("Demon")));
    }

    public ShadowbornDemon(UUID ownerId) {
        super(ownerId, 115, "Shadowborn Demon", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.expansionSetCode = "M14";
        this.subtype.add("Demon");

        this.color.setBlack(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Shadowborn Demon enters the battlefield, destroy target non-Demon creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(),false);
        Target target = new TargetCreaturePermanent(filter);
        target.setRequired(true);
        ability.addTarget(target);
        this.addAbility(ability);
        // At the beginning of your upkeep, if there are fewer than six creature cards in your graveyard, sacrifice a creature.
        TriggeredAbility triggeredAbility = new BeginningOfUpkeepTriggeredAbility(new SacrificeTargetEffect(), TargetController.YOU, false);
        target = new TargetControlledCreaturePermanent(true);
        target.setNotTarget(false);
        triggeredAbility.addTarget(target);
        this.addAbility(new ConditionalTriggeredAbility(
                triggeredAbility,
                new UnlessCondition(new CreatureCardsInControllerGraveCondition(6)),
                "At the beginning of your upkeep, if there are fewer than six creature cards in your graveyard, sacrifice a creature"));

    }

    public ShadowbornDemon(final ShadowbornDemon card) {
        super(card);
    }

    @Override
    public ShadowbornDemon copy() {
        return new ShadowbornDemon(this);
    }
}

class CreatureCardsInControllerGraveCondition implements Condition {
    private int value;

    public CreatureCardsInControllerGraveCondition(int value) {
        this.value = value;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player p = game.getPlayer(source.getControllerId());
        if (p != null && p.getGraveyard().count(new FilterCreatureCard(), game) >= value)
        {
                    return true;
        }
        return false;
    }
}