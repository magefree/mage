
package mage.cards.r;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ChooseACardNameEffect;
import mage.abilities.effects.common.continuous.GainAbilityControllerEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.filter.FilterObject;
import mage.filter.predicate.mageobject.NamePredicate;
import mage.game.Game;
import mage.players.Player;

/**
 * 5/1/2008: Runed Halo gives you protection from each object with the chosen name, whether it's a card, a token, or a copy of a spell. It doesn't matter what game zone that object is in.
 * 5/1/2008: You can still be attacked by creatures with the chosen name.
 * 5/1/2008: You'll have protection from the name, not from the word. For example, if you choose the name Forest, you'll have protection from anything named "Forest" -- but you won't have protection from Forests. An animated Sapseep Forest, for example, could deal damage to you even though its subtype is Forest.
 * 5/1/2008: You can name either half of a split card, but not both. You'll have protection from the half you named (and from a fused split spell with that name), but not the other half.
 * 5/1/2008: You can't choose [nothing] as a name. Face-down creatures have no name, so Runed Halo can't give you protection from them.
 * 5/1/2008: You must choose the name of a card, not the name of a token. For example, you can't choose "Saproling" or "Voja." However, if a token happens to have the same name as a card (such as "Shapeshifter" or "Goldmeadow Harrier"), you can choose it.
 * 5/1/2008: You may choose either one of a flip card's names. You'll have protection only from the appropriate version. For example, if you choose Nighteyes the Desecrator, you won't have protection from Nezumi Graverobber.
 *
 * @author LevelX2
 */
public final class RunedHalo extends CardImpl {

    public RunedHalo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{W}{W}");


        // As Runed Halo enters the battlefield, name a card.
        Ability ability = new AsEntersBattlefieldAbility(new ChooseACardNameEffect(ChooseACardNameEffect.TypeOfName.ALL));
        // You have protection from the chosen card name.
        ability.addEffect(new RunedHaloSetProtectionEffect());
        this.addAbility(ability);
    }

    private RunedHalo(final RunedHalo card) {
        super(card);
    }

    @Override
    public RunedHalo copy() {
        return new RunedHalo(this);
    }
}

class RunedHaloSetProtectionEffect extends OneShotEffect {

    public RunedHaloSetProtectionEffect() {
        super(Outcome.Protect);
        staticText = "<br/><br/>You have protection from the chosen card name. <i>(You can't be targeted, dealt damage, or enchanted by anything with that name.)</i>";
    }

    private RunedHaloSetProtectionEffect(final RunedHaloSetProtectionEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        String cardName = (String) game.getState().getValue(source.getSourceId().toString() + ChooseACardNameEffect.INFO_KEY);
        if (controller != null && cardName != null && !cardName.isEmpty()) {
            FilterObject filter = new FilterObject("the card name [" + cardName + ']');
            filter.add(new NamePredicate(cardName));
            ContinuousEffect effect = new GainAbilityControllerEffect(new ProtectionAbility(filter), Duration.Custom);
            game.addEffect(effect, source);
            return true;
        }
        return false;
    }

    @Override
    public RunedHaloSetProtectionEffect copy() {
        return new RunedHaloSetProtectionEffect(this);
    }

}
