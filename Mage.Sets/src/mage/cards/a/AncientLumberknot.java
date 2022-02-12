package mage.cards.a;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.ruleModifying.CombatDamageByToughnessEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AncientLumberknot extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("creature you control with toughness greater than its power");

    static {
        filter.add(AncientLumberknotPredicate.instance);
    }

    public AncientLumberknot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{G}");

        this.subtype.add(SubType.TREEFOLK);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Each creature you control with toughness greater than its power assigns combat damage equal to its toughness rather than its power.
        this.addAbility(new SimpleStaticAbility(new CombatDamageByToughnessEffect(filter, true)));
    }

    private AncientLumberknot(final AncientLumberknot card) {
        super(card);
    }

    @Override
    public AncientLumberknot copy() {
        return new AncientLumberknot(this);
    }
}

enum AncientLumberknotPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getToughness().getValue() > input.getPower().getValue();
    }
}
