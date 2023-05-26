package mage.cards.d;

import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.effects.common.AttachEffect;
import mage.abilities.effects.mana.ManaEffect;
import mage.abilities.keyword.EnchantAbility;
import mage.abilities.mana.EnchantedTappedTriggeredManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ManaChoice;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPermanent;
import mage.target.common.TargetLandPermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author Plopman
 */
public final class DawnsReflection extends CardImpl {

    public DawnsReflection(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{G}");
        this.subtype.add(SubType.AURA);

        // Enchant land
        TargetPermanent auraTarget = new TargetLandPermanent();
        this.getSpellAbility().addTarget(auraTarget);
        this.getSpellAbility().addEffect(new AttachEffect(Outcome.AddAbility));
        Ability ability = new EnchantAbility(auraTarget);
        this.addAbility(ability);

        // Whenever enchanted land is tapped for mana, its controller adds two mana in any combination of colors.
        this.addAbility(new EnchantedTappedTriggeredManaAbility(new DawnsReflectionManaEffect()));
    }

    private DawnsReflection(final DawnsReflection card) {
        super(card);
    }

    @Override
    public DawnsReflection copy() {
        return new DawnsReflection(this);
    }
}

class DawnsReflectionManaEffect extends ManaEffect {

    DawnsReflectionManaEffect() {
        super();
        this.staticText = "its controller adds an additional two mana in any combination of colors";
    }

    private DawnsReflectionManaEffect(final DawnsReflectionManaEffect effect) {
        super(effect);
    }

    @Override
    public DawnsReflectionManaEffect copy() {
        return new DawnsReflectionManaEffect(this);
    }

    @Override
    public Player getPlayer(Game game, Ability source) {
        Permanent enchantment = game.getPermanent(source.getSourceId());
        if (enchantment != null) {
            Permanent permanentAttachedTo = game.getPermanent(enchantment.getAttachedTo());
            if (permanentAttachedTo != null) {
                return game.getPlayer(permanentAttachedTo.getControllerId());
            }
        }
        return null;
    }

    @Override
    public List<Mana> getNetMana(Game game, Ability source) {
        List<Mana> netMana = new ArrayList<>();
        netMana.add(Mana.AnyMana(2));
        return netMana;
    }

    @Override
    public Mana produceMana(Game game, Ability source) {
        if (game != null) {
            Player player = getPlayer(game, source);
            return ManaChoice.chooseAnyColor(player, game, 2);
        }
        return new Mana();
    }
}
