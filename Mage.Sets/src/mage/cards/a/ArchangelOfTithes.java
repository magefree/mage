
package mage.cards.a;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.combat.CantAttackYouUnlessPayAllEffect;
import mage.abilities.effects.common.combat.CantBlockUnlessPayManaAllEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public final class ArchangelOfTithes extends CardImpl {

    public ArchangelOfTithes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}{W}{W}");
        this.subtype.add(SubType.ANGEL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // As long as Archangel of Tithes is untapped, creatures can't attack you or a planeswalker you control unless their controller pays {1} for each of those creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ArchangelOfTithesPayManaToAttackAllEffect()));

        // As long as Archangel of Tithes is attacking, creatures can't block unless their controller pays {1} for each of those creatures.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ArchangelOfTithesPayManaToBlockAllEffect()));
    }

    private ArchangelOfTithes(final ArchangelOfTithes card) {
        super(card);
    }

    @Override
    public ArchangelOfTithes copy() {
        return new ArchangelOfTithes(this);
    }
}

class ArchangelOfTithesPayManaToAttackAllEffect extends CantAttackYouUnlessPayAllEffect {

    ArchangelOfTithesPayManaToAttackAllEffect() {
        super(new ManaCostsImpl<>("{1}"), true);
        staticText = "As long as {this} is untapped, creatures can't attack you or planeswalkers you control unless their controller pays {1} for each of those creatures.";
    }

    ArchangelOfTithesPayManaToAttackAllEffect(ArchangelOfTithesPayManaToAttackAllEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (permanent.isTapped()) {
            return false;
        }
        return super.applies(event, source, game);
    }

    @Override
    public ArchangelOfTithesPayManaToAttackAllEffect copy() {
        return new ArchangelOfTithesPayManaToAttackAllEffect(this);
    }

}

class ArchangelOfTithesPayManaToBlockAllEffect extends CantBlockUnlessPayManaAllEffect {

    ArchangelOfTithesPayManaToBlockAllEffect() {
        super(new ManaCostsImpl<>("{1}"), true);
        staticText = "As long as {this} is attacking, creatures can't block unless their controller pays {1} for each of those creatures.";
    }

    ArchangelOfTithesPayManaToBlockAllEffect(ArchangelOfTithesPayManaToBlockAllEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanent(source.getSourceId());
        if (!permanent.isAttacking()) {
            return false;
        }
        return super.applies(event, source, game);
    }

    @Override
    public ArchangelOfTithesPayManaToBlockAllEffect copy() {
        return new ArchangelOfTithesPayManaToBlockAllEffect(this);
    }

}
