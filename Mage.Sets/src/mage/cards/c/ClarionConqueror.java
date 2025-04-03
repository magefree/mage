package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClarionConqueror extends CardImpl {

    public ClarionConqueror(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.DRAGON);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Activated abilities of artifacts, creatures, and planeswalkers can't be activated.
        this.addAbility(new SimpleStaticAbility(new ClarionConquerorEffect()));
    }

    private ClarionConqueror(final ClarionConqueror card) {
        super(card);
    }

    @Override
    public ClarionConqueror copy() {
        return new ClarionConqueror(this);
    }
}

class ClarionConquerorEffect extends RestrictionEffect {

    ClarionConquerorEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "activated abilities of artifacts, creatures, and planeswalkers can't be activated";
    }

    private ClarionConquerorEffect(final ClarionConquerorEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isArtifact(game) || permanent.isCreature(game) || permanent.isPlaneswalker(game);
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public ClarionConquerorEffect copy() {
        return new ClarionConquerorEffect(this);
    }
}
