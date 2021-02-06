package mage.cards.d;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.abilities.keyword.LandwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;

import java.util.UUID;

/**
 * @author MarcoMarin
 */
public final class DesertNomads extends CardImpl {

    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("desert");

    static {
        filter.add(SubType.DESERT.getPredicate());
    }

    public DesertNomads(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOMAD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Desertwalk
        this.addAbility(new LandwalkAbility(filter));

        // Prevent all damage that would be dealt to Desert Nomads by Deserts.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new PreventDamageToSourceBySubtypeEffect(SubType.DESERT)));
    }

    private DesertNomads(final DesertNomads card) {
        super(card);
    }

    @Override
    public DesertNomads copy() {
        return new DesertNomads(this);
    }
}

class PreventDamageToSourceBySubtypeEffect extends PreventAllDamageToSourceEffect {

    private SubType subtype;

    public PreventDamageToSourceBySubtypeEffect(SubType sub) {
        super(Duration.WhileOnBattlefield);
        subtype = sub;
        staticText = "Prevent all damage that would be dealt to {this} by " + subtype.getDescription();
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            MageObject sourceObject = game.getObject(event.getSourceId());
            if (sourceObject != null && sourceObject.hasSubtype(subtype, game)) {
                return event.getTargetId().equals(source.getSourceId());
            }
        }
        return false;
    }
}
    

    
