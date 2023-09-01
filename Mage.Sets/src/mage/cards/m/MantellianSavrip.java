package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.MonstrosityAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author Styxo
 */
public final class MantellianSavrip extends CardImpl {

    public MantellianSavrip(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}");
        this.subtype.add(SubType.MANTELLIAN);
        this.subtype.add(SubType.BERSERKER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // {5}{G}{G}: Monstrosity 2.
        this.addAbility(new MonstrosityAbility("{5}{G}{G}", 2));

        // Creatures with power less than Mantellian Savrip's power can't block it.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new MantellianSavripRestrictionEffect()));

    }

    private MantellianSavrip(final MantellianSavrip card) {
        super(card);
    }

    @Override
    public MantellianSavrip copy() {
        return new MantellianSavrip(this);
    }
}

class MantellianSavripRestrictionEffect extends RestrictionEffect {

    public MantellianSavripRestrictionEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Creatures with power less than {this}'s power can't block it";
    }

    private MantellianSavripRestrictionEffect(final MantellianSavripRestrictionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.getId().equals(source.getSourceId());
    }

    @Override
    public boolean canBeBlocked(Permanent attacker, Permanent blocker, Ability source, Game game, boolean canUseChooseDialogs) {
        return (blocker.getPower().getValue() >= attacker.getPower().getValue());
    }

    @Override
    public MantellianSavripRestrictionEffect copy() {
        return new MantellianSavripRestrictionEffect(this);
    }
}
