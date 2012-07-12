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
package mage.sets.scarsofmirrodin;

import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.abilities.effects.common.continious.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.filter.FilterPermanent;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author nantuko
 */
public class GolemArtisan extends CardImpl<GolemArtisan> {

    private static final FilterPermanent filter = new FilterPermanent("artifact creature");

    static {
        filter.add(new CardTypePredicate(CardType.ARTIFACT));
        filter.add(new CardTypePredicate(CardType.CREATURE));
    }

    public GolemArtisan(UUID ownerId) {
        super(ownerId, 159, "Golem Artisan", Rarity.UNCOMMON, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        this.expansionSetCode = "SOM";
        this.subtype.add("Golem");

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}: Target artifact creature gets +1/+1 until end of turn.
        Ability ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new BoostTargetEffect(1, 1, Constants.Duration.EndOfTurn), new GenericManaCost(2));
        Target target = new TargetPermanent(filter);
        ability.addTarget(target);
        this.addAbility(ability);

        // {2}: Target artifact creature gains your choice of flying, trample, or haste until end of turn.
        ability = new SimpleActivatedAbility(Constants.Zone.BATTLEFIELD, new GolemArtisanEffect(), new GenericManaCost(2));
        target = new TargetPermanent(filter);
        ability.addTarget(target);
        this.addAbility(ability);

    }

    public GolemArtisan(final GolemArtisan card) {
        super(card);
    }

    @Override
    public GolemArtisan copy() {
        return new GolemArtisan(this);
    }
}

class GolemArtisanEffect extends OneShotEffect<GolemArtisanEffect> {
    GolemArtisanEffect() {
        super(Constants.Outcome.AddAbility);
        staticText = "Target artifact creature gains your choice of flying, trample, or haste until end of turn";
    }

    GolemArtisanEffect(final GolemArtisanEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getPermanent(targetPointer.getFirst(game, source));
        Player playerControls = game.getPlayer(source.getControllerId());
        if (permanent != null && playerControls != null) {
            Choice abilityChoice = new ChoiceImpl();
            abilityChoice.setMessage("Choose an ability to add");

            Set<String> abilities = new HashSet<String>();
            abilities.add(FlyingAbility.getInstance().getRule());
            abilities.add(TrampleAbility.getInstance().getRule());
            abilities.add(HasteAbility.getInstance().getRule());
            abilityChoice.setChoices(abilities);
            playerControls.choose(Constants.Outcome.AddAbility, abilityChoice, game);

            String chosen = abilityChoice.getChoice();
            Ability ability = null;
            if (FlyingAbility.getInstance().getRule().equals(chosen)) {
                ability = FlyingAbility.getInstance();
            } else if (TrampleAbility.getInstance().getRule().equals(chosen)) {
                ability = TrampleAbility.getInstance();
            } else if (HasteAbility.getInstance().getRule().equals(chosen)) {
                ability = HasteAbility.getInstance();
            }

            if (ability != null) {
                ContinuousEffect effect = new GainAbilityTargetEffect(ability, Constants.Duration.EndOfTurn);
                game.addEffect(effect, source);
                return true;
            }
        }

        return false;
    }

    @Override
    public GolemArtisanEffect copy() {
        return new GolemArtisanEffect(this);
    }

}
