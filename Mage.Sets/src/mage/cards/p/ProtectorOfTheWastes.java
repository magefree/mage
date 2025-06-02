package mage.cards.p;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterArtifactOrEnchantmentPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Jmlundeen
 */
public final class ProtectorOfTheWastes extends CardImpl {

    public ProtectorOfTheWastes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{W}{W}");
        
        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When this creature enters or becomes monstrous, exile up to two target artifacts and/or enchantments controlled by different players.
        Ability ability = new ProtectorOfTheWastesTriggeredAbility();
        ability.addTarget(new ProtectorOfTheWastesTarget());
        this.addAbility(ability);

        // {4}{W}: Monstrosity 3.
        this.addAbility(new MonstrosityAbility("{4}{W}", 3));
    }

    private ProtectorOfTheWastes(final ProtectorOfTheWastes card) {
        super(card);
    }

    @Override
    public ProtectorOfTheWastes copy() {
        return new ProtectorOfTheWastes(this);
    }
}

class ProtectorOfTheWastesTarget extends TargetPermanent {

    private static final FilterArtifactOrEnchantmentPermanent filter = new FilterArtifactOrEnchantmentPermanent(
            "artifacts and/or enchantments controlled by different players");

    ProtectorOfTheWastesTarget() {
        super(0, 2, filter);
    }

    private ProtectorOfTheWastesTarget(ProtectorOfTheWastesTarget target) {
        super(target);
    }

    @Override
    public ProtectorOfTheWastesTarget copy() {
        return new ProtectorOfTheWastesTarget(this);
    }

    @Override
    public boolean canTarget(UUID id, Ability source, Game game) {
        if (!super.canTarget(id, source, game)) {
            return false;
        }
        Permanent permanent = game.getPermanent(id);
        if (permanent == null) {
            return false;
        }
        return this.getTargets().stream()
                .map(game::getPermanent)
                .filter(Objects::nonNull)
                .noneMatch(perm -> !perm.getId().equals(permanent.getId())
                        && perm.isControlledBy(permanent.getControllerId()));
    }

    @Override
    public Set<UUID> possibleTargets(UUID sourceControllerId, Ability source, Game game) {
        Set<UUID> possibleTargets = new HashSet<>();
        MageObject targetSource = game.getObject(source);
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, sourceControllerId, source, game)) {
            boolean validTarget = this.getTargets().stream()
                    .map(game::getPermanent)
                    .filter(Objects::nonNull)
                    .noneMatch(perm -> !perm.getId().equals(permanent.getId()) && perm.isControlledBy(permanent.getControllerId()));
            if (validTarget) {
                if (notTarget || permanent.canBeTargetedBy(targetSource, sourceControllerId, source, game)) {
                    possibleTargets.add(permanent.getId());
                }
            }
        }
        return possibleTargets;
    }
}

class ProtectorOfTheWastesTriggeredAbility extends TriggeredAbilityImpl {

    public ProtectorOfTheWastesTriggeredAbility() {
        super(Zone.ALL, new ExileTargetEffect());
        setTriggerPhrase("When {this} enters or becomes monstrous, ");
    }

    private ProtectorOfTheWastesTriggeredAbility(final ProtectorOfTheWastesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ProtectorOfTheWastesTriggeredAbility copy() {
        return new ProtectorOfTheWastesTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.BECOMES_MONSTROUS
                || event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getSourceId().equals(this.getSourceId());
    }
}