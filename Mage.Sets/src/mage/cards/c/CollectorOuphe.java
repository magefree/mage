package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.RestrictionEffect;
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
public final class CollectorOuphe extends CardImpl {

    public CollectorOuphe(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.OUPHE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Activated abilities of artifacts can't be activated.
        this.addAbility(new SimpleStaticAbility(new CollectorOupheEffect()));
    }

    private CollectorOuphe(final CollectorOuphe card) {
        super(card);
    }

    @Override
    public CollectorOuphe copy() {
        return new CollectorOuphe(this);
    }
}

class CollectorOupheEffect extends RestrictionEffect {

    CollectorOupheEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Activated abilities of artifacts can't be activated";
    }

    private CollectorOupheEffect(final CollectorOupheEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        return permanent.isArtifact(game);
    }

    @Override
    public boolean canUseActivatedAbilities(Permanent permanent, Ability source, Game game, boolean canUseChooseDialogs) {
        return false;
    }

    @Override
    public CollectorOupheEffect copy() {
        return new CollectorOupheEffect(this);
    }

}