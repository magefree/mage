
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author North
 */
public final class CentaurOmenreader extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature spells");

    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    public CentaurOmenreader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}");
        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.CENTAUR);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // As long as Centaur Omenreader is tapped, creature spells you cast cost {2} less to cast.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CentaurOmenreaderSpellsCostReductionEffect(filter)));
    }

    private CentaurOmenreader(final CentaurOmenreader card) {
        super(card);
    }

    @Override
    public CentaurOmenreader copy() {
        return new CentaurOmenreader(this);
    }
}

class CentaurOmenreaderSpellsCostReductionEffect extends SpellsCostReductionControllerEffect {

    public CentaurOmenreaderSpellsCostReductionEffect(FilterCard filter) {
        super(filter, 2);
        staticText = "As long as {this} is tapped, creature spells you cast cost {2} less to cast";
    }

    protected CentaurOmenreaderSpellsCostReductionEffect(SpellsCostReductionControllerEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(Ability abilityToModify, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null && sourcePermanent.isTapped()) {
            return super.applies(abilityToModify, source, game);
        }
        return false;
    }

    @Override
    public CentaurOmenreaderSpellsCostReductionEffect copy() {
        return new CentaurOmenreaderSpellsCostReductionEffect(this);
    }
}
