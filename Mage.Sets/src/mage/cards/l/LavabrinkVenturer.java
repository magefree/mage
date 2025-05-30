package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ChooseModeEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ModeChoice;
import mage.constants.SubType;
import mage.filter.FilterObject;
import mage.filter.predicate.mageobject.ManaValueParityPredicate;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LavabrinkVenturer extends CardImpl {

    public LavabrinkVenturer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // As Lavabrink Venturer enters the battlefield, choose odd or even.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseModeEffect(ModeChoice.ODD, ModeChoice.EVEN)));

        // Lavabrink Venturer has protection from each converted mana cost of the chosen value.
        this.addAbility(new SimpleStaticAbility(new LavabrinkVenturerEffect()));
    }

    private LavabrinkVenturer(final LavabrinkVenturer card) {
        super(card);
    }

    @Override
    public LavabrinkVenturer copy() {
        return new LavabrinkVenturer(this);
    }
}

class LavabrinkVenturerEffect extends GainAbilitySourceEffect {

    private static final FilterObject nullFilter = new FilterObject("nothing");
    private static final FilterObject oddFilter = new FilterObject("odd mana values");
    private static final FilterObject evenFilter = new FilterObject("even mana values");

    static {
        nullFilter.add(ManaValueParityPredicate.ODD);
        nullFilter.add(ManaValueParityPredicate.EVEN);
        oddFilter.add(ManaValueParityPredicate.ODD);
        evenFilter.add(ManaValueParityPredicate.EVEN);
    }

    LavabrinkVenturerEffect() {
        super(new ProtectionAbility(nullFilter));
        this.ability.setRuleVisible(false);
        staticText = "{this} has protection from each mana value of the chosen quality. <i>(Zero is even.)</i>";
    }

    private LavabrinkVenturerEffect(final LavabrinkVenturerEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (ModeChoice.ODD.checkMode(game, source)) {
            this.ability = new ProtectionAbility(oddFilter);
        } else if (ModeChoice.EVEN.checkMode(game, source)) {
            this.ability = new ProtectionAbility(evenFilter);
        } else {
            return false;
        }
        return super.apply(game, source);
    }

    @Override
    public LavabrinkVenturerEffect copy() {
        return new LavabrinkVenturerEffect(this);
    }
}
