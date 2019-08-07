package mage.cards.c;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.target.common.TargetControlledPermanent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CliffsideRescuer extends CardImpl {

    public CliffsideRescuer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.KOR);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // {T}, Sacrifice Cliffside Rescuer: Target permanent you control gets protection from each opponent until end of turn.
        Ability ability = new SimpleActivatedAbility(new CliffsideRescuerEffect(), new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetControlledPermanent());
        this.addAbility(ability);
    }

    private CliffsideRescuer(final CliffsideRescuer card) {
        super(card);
    }

    @Override
    public CliffsideRescuer copy() {
        return new CliffsideRescuer(this);
    }
}

class CliffsideRescuerEffect extends OneShotEffect {

    CliffsideRescuerEffect() {
        super(Outcome.Benefit);
        staticText = "Target permanent you control gets protection from each opponent until end of turn.";
    }

    private CliffsideRescuerEffect(final CliffsideRescuerEffect effect) {
        super(effect);
    }

    @Override
    public CliffsideRescuerEffect copy() {
        return new CliffsideRescuerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        game.addEffect(new GainAbilityTargetEffect(new CliffsideRescuerProtectionAbility(
                game.getOpponents(source.getControllerId())
        ), Duration.EndOfTurn), source);
        return true;
    }
}

class CliffsideRescuerProtectionAbility extends ProtectionAbility {

    private final Set<UUID> playerSet = new HashSet<>();

    CliffsideRescuerProtectionAbility(Set<UUID> playerSet) {
        super(StaticFilters.FILTER_CARD);
        this.playerSet.addAll(playerSet);
    }

    private CliffsideRescuerProtectionAbility(final CliffsideRescuerProtectionAbility ability) {
        super(ability);
        this.playerSet.addAll(ability.playerSet);
    }

    @Override
    public CliffsideRescuerProtectionAbility copy() {
        return new CliffsideRescuerProtectionAbility(this);
    }

    @Override
    public String getRule() {
        return "{this} has protection from each opponent";
    }

    @Override
    public boolean canTarget(MageObject source, Game game) {
        if (source == null) {
            return true;
        }
        if (source instanceof Permanent) {
            return playerSet.stream().noneMatch(((Permanent) source)::isControlledBy);
        }
        if (source instanceof Spell) {
            return playerSet.stream().noneMatch(((Spell) source)::isControlledBy);
        }
        if (source instanceof StackObject) {
            return playerSet.stream().noneMatch(((StackObject) source)::isControlledBy);
        }
        if (source instanceof Card) { // e.g. for Vengeful Pharaoh
            return playerSet.stream().noneMatch(((Card) source)::isOwnedBy);
        }
        return true;
    }
}
