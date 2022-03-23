package mage.cards.o;

import mage.abilities.Ability;
import mage.abilities.common.DiesAttachedTriggeredAbility;
import mage.abilities.common.PutIntoGraveFromBattlefieldSourceTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlAttachedEffect;
import mage.abilities.effects.common.continuous.BoostEquippedEffect;
import mage.abilities.keyword.EquipAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class OathkeeperTakenosDaisho extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("it's a Samurai card");

    static {
        filter.add(SubType.SAMURAI.getPredicate());
    }

    public OathkeeperTakenosDaisho(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.EQUIPMENT);

        // Equipped creature gets +3/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostEquippedEffect(3, 1, Duration.WhileOnBattlefield)));

        // Whenever equipped creature dies, return that card to the battlefield under your control if it's a Samurai card.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new DiesAttachedTriggeredAbility(new ReturnToBattlefieldUnderYourControlAttachedEffect(), "equipped creature", false),
                new OathkeeperEquippedMatchesFilterCondition(filter),
                ""));

        // When Oathkeeper, Takeno's Daisho is put into a graveyard from the battlefield, exile equipped creature.
        this.addAbility(new PutIntoGraveFromBattlefieldSourceTriggeredAbility(new ExileEquippedEffect()));

        // Equip {2}
        this.addAbility(new EquipAbility(Outcome.BoostCreature, new ManaCostsImpl("{2}")));
    }

    private OathkeeperTakenosDaisho(final OathkeeperTakenosDaisho card) {
        super(card);
    }

    @Override
    public OathkeeperTakenosDaisho copy() {
        return new OathkeeperTakenosDaisho(this);
    }
}

class ExileEquippedEffect extends OneShotEffect {

    public ExileEquippedEffect() {
        super(Outcome.Exile);
        staticText = "exile equipped creature";
    }

    public ExileEquippedEffect(final ExileEquippedEffect effect) {
        super(effect);
    }

    @Override
    public ExileEquippedEffect copy() {
        return new ExileEquippedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent equipment = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (equipment != null && equipment.getAttachedTo() != null) {
            Permanent creature = game.getPermanent(equipment.getAttachedTo());
            if (creature != null) {
                return creature.moveToExile(null, "", source, game);
            }
        }
        return false;
    }

}

class OathkeeperEquippedMatchesFilterCondition implements Condition {

    private FilterCreaturePermanent filter;

    public OathkeeperEquippedMatchesFilterCondition(FilterCreaturePermanent filter) {
        this.filter = filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = game.getBattlefield().getPermanent(source.getSourceId());
        if (permanent == null) {
            permanent = (Permanent) game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        }
        if (permanent != null) {
            Permanent attachedTo = null;
            if (permanent.getAttachedTo() != null) {
                attachedTo = game.getBattlefield().getPermanent(permanent.getAttachedTo());
                if (attachedTo == null) {
                    attachedTo = (Permanent) game.getLastKnownInformation(permanent.getAttachedTo(), Zone.BATTLEFIELD);
                }
            }
            if (attachedTo == null) {
                for (Effect effect : source.getEffects()) {
                    attachedTo = (Permanent) effect.getValue("attachedTo");
                }
            }
            if (attachedTo != null) {
                return filter.match(attachedTo, attachedTo.getControllerId(), source, game);

            }
        }
        return false;
    }
}