package mage.cards.t;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.BargainedCondition;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.cost.SpellCostIncreaseSourceEffect;
import mage.abilities.keyword.BargainAbility;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterSpellOrPermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.target.common.TargetSpellOrPermanent;
import mage.util.PerpetualUtil;

import java.util.UUID;

public final class TalionsThroneguard extends CardImpl {

    private static final FilterSpellOrPermanent filter = new FilterSpellOrPermanent("spell or nonland permanent");

    static {
        filter.getPermanentFilter().add(Predicates.not(CardType.LAND.getPredicate()));
    }

    public TalionsThroneguard(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{U}");
        this.subtype.add(SubType.FAERIE);
        this.subtype.add(SubType.WIZARD);
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        this.addAbility(FlashAbility.getInstance());
        this.addAbility(new BargainAbility());
        this.addAbility(FlyingAbility.getInstance());
        Ability ability = new EntersBattlefieldTriggeredAbility(new TalionsThroneguardEffect());
        ability.addTarget(new TargetSpellOrPermanent(0, 1, filter, false));
        this.addAbility(ability);
    }

    private TalionsThroneguard(final TalionsThroneguard card) {
        super(card);
    }

    @Override
    public TalionsThroneguard copy() {
        return new TalionsThroneguard(this);
    }
}

class TalionsThroneguardEffect extends OneShotEffect {

    TalionsThroneguardEffect() {
        super(Outcome.Benefit);
        staticText = "return up to one target spell or nonland permanent to its owner's hand. If {this} was bargained, that card perpetually gains \"This spell costs {2} more to cast.\"";
    }

    private TalionsThroneguardEffect(final TalionsThroneguardEffect effect) {
        super(effect);
    }

    @Override
    public TalionsThroneguardEffect copy() {
        return new TalionsThroneguardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source.getFirstTarget());
        Card card = object instanceof Spell ? ((Spell) object).getCard() : game.getCard(source.getFirstTarget());
        boolean isCopy = object instanceof Spell && object.isCopy();
        boolean bargained = BargainedCondition.instance.apply(game, source);
        new ReturnToHandTargetEffect().setTargetPointer(getTargetPointer().copy()).apply(game, source);
        if (bargained && card != null && !isCopy) {
            PerpetualUtil.addAbility(card, new SimpleStaticAbility(Zone.ALL,
                    new SpellCostIncreaseSourceEffect(2)), source, game);
        }
        return true;
    }
}
